package edu.vuum.mocca.authentication;

public interface AccountAuthNetworkInterface {
	public String userSignUp(final String name, final String email,
			final String pass, String authType) throws Exception;

	public String userSignIn(final String user, final String pass,
			String authType) throws Exception;
}
