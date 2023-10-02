package com.nttdata.ejercicio.equipo1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nttdata.ejercicio.equipo1.domain.Domicilio;
import com.nttdata.ejercicio.equipo1.domain.Persona;
import com.nttdata.ejercicio.equipo1.repository.DomicilioRepository;
import com.nttdata.ejercicio.equipo1.repository.PersonaRepository;
import com.nttdata.ejercicio.equipo1.security.JWTAuthorizationFilter;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers( HttpMethod.POST, "/user").permitAll()
					.anyRequest().authenticated();

			http.cors();
		}

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedOrigins("*")
							.allowedMethods("*")
							.allowedHeaders("*");
				}
			};
		}
	}
	
	@Bean
    public Object iniciarBBDD(DomicilioRepository domicilioRepository , PersonaRepository personaRepository) {
        Set<Persona> personas = new HashSet<>();
        personas.add(new Persona(44298830,"Facundo","Piana Sampietro"));
        personas.add(new Persona(42567341,"Agustín","Decouflet"));
        personas.add(new Persona(43527142,"Franco David","Singer"));
        personas.add(new Persona(42888000,"Germán","Pérez"));
        personas.add(new Persona(36123000,"Sebatián","Hernández"));
        personas.add(new Persona(43527142,"Román","Martínez"));
        personas.add(new Persona(43527142,"Rodrigo","De paul"));
        personas.add(new Persona(42888000,"Julián","Álvarez"));
        personas.add(new Persona(36123000,"Emiliano","Martínez"));
        personas.add(new Persona(43527142,"Nahuel","Molina"));
        personaRepository.saveAll(personas);
        
        Set<Domicilio> domicilios = new HashSet<>();
        domicilios.add(new Domicilio("Saavedra","2136"));
        domicilios.add(new Domicilio("Castelli", "4500"));
        domicilios.add(new Domicilio("Edison", "1400"));
        domicilios.add(new Domicilio("Santa fe","5030"));
        domicilios.add(new Domicilio("Yapeyú", "250"));
        domicilios.add(new Domicilio("La araña", "3500"));
        domicilioRepository.saveAll(domicilios);
        
        return "OK";
    }
	
	

}

