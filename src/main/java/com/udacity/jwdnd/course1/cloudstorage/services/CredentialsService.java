package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialsData;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CredentialsService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public CredentialsData[] getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public int createCredential(CredentialsData credential) {
        encryptPassword(credential);
        return credentialMapper.createCredential(credential);
    }

    public int editCredential(CredentialsData credential) {
        encryptPassword(credential);
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(int credentialid) {
        return credentialMapper.deleteCredential(credentialid);
    }

    public CredentialsData[] getUserCredentials(int userid) {
        return credentialMapper.getUserCredentials(userid);
    }

    private void encryptPassword(CredentialsData credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
    }


    public boolean checkCredentialUsernameDuplication(CredentialsData credential) {
        CredentialsData[] allCredentials = getAllCredentials();

        for (int i = 0; i < allCredentials.length; i++)
            if (credential.getUsername().equals(allCredentials[i].getUsername()))
                return true;

        return false;
    }


}
