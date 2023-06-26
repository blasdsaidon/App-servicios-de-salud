package com.proyectofinal.salud;

import com.proyectofinal.salud.servicios.adminServicio;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public pacienteServicio pacienteServicio;
    @Autowired
    public medicoServicio medicoServicio;
    @Autowired
    public adminServicio adminServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pacienteServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(medicoServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(adminServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().authorizeRequests()
                .antMatchers("/paciente/*").hasRole("USER")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().authorizeRequests()
                .antMatchers("/medico/*").hasRole("PROFESIONAL")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and().csrf()
                .disable();
    }
}
