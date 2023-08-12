package com.spaco_apoio.api.utility;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Component
public class UtilSecurity
{
	private static final String STR = "#";
	private static final String ENCODE = "utf-8";
	private static final String BLOWFISH = "Blowfish";
	private static String CRYPTO;
	public static final String ANONYMOUS_USER = "anonymous_user000";
	
	@Value("${security.obfuscate_secret}")
	private void setCrypto(String crypto)
	{
		UtilSecurity.CRYPTO = crypto;
	}
	
	public static String encryptId(Long param)
	{
		if(param == null)
		{
			return null;
		}

		Authentication jwtAuth = SecurityContextHolder.getContext().getAuthentication();
		String user = ANONYMOUS_USER;
		
		if (jwtAuth != null)
		{
			user = jwtAuth.getPrincipal().toString();
		}

		byte[] keyData = CRYPTO.getBytes();
		SecretKeySpec KS = new SecretKeySpec(keyData, BLOWFISH);
		Cipher cipher;
		try
		{
			cipher = Cipher.getInstance(BLOWFISH);
			cipher.init(Cipher.ENCRYPT_MODE, KS);
			byte[] param_crypto = param.toString().concat(STR).concat(user).getBytes(ENCODE);
			byte[] crypto_data = cipher.doFinal(param_crypto);
			return Hex.encodeHexString(crypto_data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e)
		{
			throw new RuntimeCryptoException("Error");
		}
	}
	
	public static Long decryptId(String param)
	{
		if (param == null)
		{
			return null;
		}

		Authentication jwtAuth = SecurityContextHolder.getContext().getAuthentication();
		String user = ANONYMOUS_USER;
		
		if (jwtAuth != null)
		{
			user = jwtAuth.getPrincipal().toString();
		}

		byte[] keyData = CRYPTO.getBytes();
		SecretKeySpec KS = new SecretKeySpec(keyData, BLOWFISH);
		String param_decrypto = "";
		try
		{
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, KS);
			byte[] crypto_data = Hex.decodeHex(param);
			param_decrypto = new String(cipher.doFinal(crypto_data));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | DecoderException | IllegalBlockSizeException | BadPaddingException e)
		{
			throw new RuntimeCryptoException("PTR-024");
		}

		if (!user.equals(param_decrypto.split(STR)[1]))
		{
			throw new RuntimeCryptoException("Data tampering");
		}

		return Long.valueOf(param_decrypto.split(STR)[0]);
	}
}
