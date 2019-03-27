package info.niteshjha.securityConfig;

import info.niteshjha.config.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public UserSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence userEnteredPassword) {
                return getMd5(userEnteredPassword.toString());
            }

            @Override
            public boolean matches(CharSequence userEnteredPassword, String storedPassword) {
                return Objects.equals(getMd5(userEnteredPassword.toString()), getMd5(storedPassword));
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //@formatter:off
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        //@formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {   //@formatter:off
        http.authorizeRequests()
                .antMatchers("/login*",
                                         "/",
                                         "/resetPassword*",
                                         "/signup*",
                                         "/forgotPassword*",
                                         "/js/**",
                                         "/img/**",
                                         "/css/**",
                                         "/user/forgotPassword*",
                                         "/changePassword*",
                                         "/saveUser*",
                                         "/confirmEmail*").permitAll()
                .anyRequest().authenticated()

                .and().formLogin()
                            .loginPage("/login")
                            .permitAll()
                            .loginProcessingUrl("/doLogin")
                            .successForwardUrl("/listUser")
                .and().logout().permitAll()
                            .logoutUrl("/doLogout")
                            .logoutSuccessUrl("/login?logout")

                .and().csrf().disable()
                .rememberMe()
                    .key("myprivatekeyoverhere")
                    .rememberMeCookieName("nRemCook")
                    .rememberMeParameter("remM2Cook")
                    .tokenValiditySeconds(900);
                  //  .useSecureCookie(true);

        //@formatter:on
    }


    private static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);
            return null;
        }
    }


}
