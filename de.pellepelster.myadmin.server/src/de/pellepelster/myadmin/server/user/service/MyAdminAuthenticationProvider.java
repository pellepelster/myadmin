package de.pellepelster.myadmin.server.user.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import de.pellepelster.myadmin.db.IBaseDAO;

public class MyAdminAuthenticationProvider implements AuthenticationProvider
{

	private Logger logger = Logger.getLogger(MyAdminAuthenticationProvider.class);

	@Resource
	private UserDetailsService userDetailsService;

	@Resource
	private IBaseDAO baseDAO;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		return new UsernamePasswordAuthenticationToken(null, null);
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(clazz);
	}

	public void setUserDetailsService(UserDetailsService userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}

	public void setBaseDAO(IBaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

}