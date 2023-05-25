package cz.muni.fi.pa165.client;

import cz.muni.fi.pa165.core.CoreApi;
import cz.muni.fi.pa165.icehockeymanager.invoker.ApiClient;
import cz.muni.fi.pa165.icehockeymanager.invoker.ApiException;
import cz.muni.fi.pa165.model.dto.HockeyPlayerDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.ConnectException;
import java.time.Instant;
import java.util.List;
import java.util.TreeSet;

@Controller
public class ClientController {

    @Value("${core.url}")
    private String coreUrl;

    private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OidcUser user) {
        log.debug("********************************************************");
        log.debug("* index() called                                       *");
        log.debug("********************************************************");
        log.debug("user {}", user == null ? "is anonymous" : user.getSubject());

        model.addAttribute("user", user);

        if (user != null) {
            model.addAttribute("issuerName",
                    "https://oidc.muni.cz/oidc/".equals(user.getIssuer().toString()) ? "MUNI" : "Google");
        }

        return "index";
    }

    @GetMapping("/hockey-players")
    public String hockeyPlayer(Model model,
                               @ModelAttribute HockeyPlayerForm hockeyPlayerForm,
                               @AuthenticationPrincipal OidcUser user,
                               @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oauth2Client) {
        OAuth2AccessToken accessToken = oauth2Client.getAccessToken();

        // call Core API
        CoreApi coreApi = getCoreApi(accessToken);
        try {
            List<HockeyPlayerDto> hockeyPlayerDtoList = coreApi.hockeyPlayersGetAll();
            model.addAttribute("hockeyPlayerDtoList", hockeyPlayerDtoList);
        } catch (ApiException apiException) {
            handleApiException(model, apiException);
        }

        // for display in heading
        model.addAttribute("user", user);
        model.addAttribute("scopes", new TreeSet<>(accessToken.getScopes()));
        model.addAttribute("token", "Bearer " + accessToken.getTokenValue());

        // default form
        hockeyPlayerForm.setFirstName("Steve");
        hockeyPlayerForm.setLastName("Jons");
        hockeyPlayerForm.setDateOfBirth(Instant.now());
        hockeyPlayerForm.setPosition("attacker");
        hockeyPlayerForm.setSkating(968);
        hockeyPlayerForm.setPhysical(753);
        hockeyPlayerForm.setShooting(992);
        hockeyPlayerForm.setDefense(246);
        hockeyPlayerForm.setPuckSkills(953);
        hockeyPlayerForm.setSenses(298);
        hockeyPlayerForm.setTeamDto(null);

        return "hockey-players";
    }

    @PostMapping("/hockey-players")
    public String createHockeyPlayer(@Valid @ModelAttribute HockeyPlayerForm hockeyPlayerForm,
                              BindingResult bindingResult,
                              Model model,
                              @AuthenticationPrincipal OidcUser user,
                              @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oauth2Client) {

        // for displaying user and scopes in heading
        OAuth2AccessToken accessToken = oauth2Client.getAccessToken();
        model.addAttribute("user", user);
        model.addAttribute("scopes", accessToken.getScopes());

        CoreApi coreApi = getCoreApi(accessToken);
        if (bindingResult.hasErrors()) {
            try {
                List<HockeyPlayerDto> hockeyPlayerDtoList = coreApi.hockeyPlayersGetAll();
                model.addAttribute("hockeyPlayerDtoList", hockeyPlayerDtoList);
            } catch (ApiException apiException) {
                handleApiException(model, apiException);
            }
            return "hockey-players";
        }
        // create hockey player
        try {
            coreApi.hockeyPlayersCreate(toDto(hockeyPlayerForm));
        } catch (ApiException apiException) {
            handleApiException(model, apiException);
            return "hockey-players";
        }

        // redirect-after-post to clear the post from browser history
        return "redirect:/hockey-players";
    }

    private void handleApiException(Model model, ApiException apiException) {
        Throwable cause = apiException.getCause();
        if (cause instanceof ConnectException) {
            log.error("cannot call API, resource server is down");
            model.addAttribute("connect_problem", true);
        } else if (cause != null) {
            log.error("cannot call API", cause);
            model.addAttribute("exception", cause);
        } else if (apiException.getCode() == HttpStatus.FORBIDDEN.value()) {
            log.error("API returns HTTP status FORBIDDEN");
            model.addAttribute("api_forbidden", true);
            model.addAttribute("wwwAuthenticate", apiException.getResponseHeaders()
                    .firstValue("www-authenticate").orElse(null));
        } else {
            log.error("API returns an error: " + apiException.getMessage());
            model.addAttribute("api_problem", true);
            model.addAttribute("api_exception", apiException);
        }
    }

    private HockeyPlayerDto toDto(HockeyPlayerForm hockeyPlayerForm) {
        return new HockeyPlayerDto(
                hockeyPlayerForm.getId(),
                hockeyPlayerForm.getFirstName(),
                hockeyPlayerForm.getLastName(),
                Instant.now(),
                "attacker",
                0,
                0,
                0,
                0,
                0,
                0,
                hockeyPlayerForm.getTeamDto()
        );
    }

    private CoreApi getCoreApi(OAuth2AccessToken token) {
        ApiClient apiClient = new ApiClient();
        apiClient.updateBaseUri(coreUrl);
        apiClient.setRequestInterceptor(httpRequestBuilder -> {
            log.debug("Bearer " + token);
            httpRequestBuilder.header("Authorization", "Bearer " + token.getTokenValue());
        });

        return new CoreApi(apiClient);
    }
}
