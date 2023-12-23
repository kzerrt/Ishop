package com.fc.ishop.security;


/**
 * @author florence
 * @date 2023/12/2
 */

/*@Order(20)// 修改filter执行顺序
@Configuration@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)*/ //extends WebSecurityConfigurerAdapter
public class SecurityConfig  {

    /*@Autowired
    private IgnoreProperties ignoreProperties;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;
    @Autowired
    private Cache<String> cache;*/

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        final ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
                = http.authorizeRequests();
        for (String url : ignoreProperties.getUrls()) {
            //log.debug("ignored-url: {}",url);
            registry.antMatchers(url).permitAll();
        }
        registry
                .and()
                // 禁止网页iframe
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // 任何请求
                .anyRequest()
                // 认证后使用
                .authenticated()
                .and()
                // 允许跨域
                .cors().configurationSource(corsConfigurationSource).and()
                // 关闭跨站请求防护
                .csrf().disable()
                // 前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                // 添加JWT认证过滤器
               .addFilter(new AuthenticationFilter(authenticationManager(), cache));
    }*/
}
