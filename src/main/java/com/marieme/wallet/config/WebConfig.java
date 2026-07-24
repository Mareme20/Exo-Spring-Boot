package com.marieme.wallet.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.io.IOException;

/**
 * Configuration Web qui ajoute un filtre pour normaliser le parametre {@code sort}
 * envoye par Swagger UI.
 * <p>
 * Swagger UI envoie {@code sort=["nom"]} au lieu de {@code sort=nom}.
 * Ce filtre transforme le format JSON array en format standard.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {

    @Bean
    public FilterRegistrationBean<SortParameterFilter> sortParameterFilter() {
        FilterRegistrationBean<SortParameterFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SortParameterFilter());
        registration.addUrlPatterns("/api/v1/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * Filtre qui intercepte les requetes et nettoie le parametre {@code sort}.
     */
    private static class SortParameterFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                             FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            String sortParam = httpRequest.getParameter("sort");

            if (sortParam != null && (sortParam.startsWith("[") || sortParam.startsWith("\""))) {
                // Nettoyer le format JSON array ou string quote
                // Exemples:
                //   ["nom"]      -> nom
                //   ["nom,asc"]  -> nom,asc
                //   ["nom","email"] -> nom,email (multiple sorts)
                String cleaned = sortParam
                        .replaceAll("^\\[", "")
                        .replaceAll("\\]$", "")
                        .replaceAll("\"", "")
                        .trim();

                // Creer un wrapper pour modifier les parametres
                HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                    @Override
                    public String getParameter(String name) {
                        if ("sort".equals(name)) {
                            return cleaned;
                        }
                        return super.getParameter(name);
                    }

                    @Override
                    public String[] getParameterValues(String name) {
                        if ("sort".equals(name)) {
                            // Si le nettoyage contient des virgules, c'est un tri multiple
                            // ex: "nom,asc" -> un seul param, Spring Data sait le parser
                            return new String[]{cleaned};
                        }
                        return super.getParameterValues(name);
                    }
                };

                filterChain.doFilter(wrapper, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
