/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient.security;

import java.util.Arrays;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 * Simple in-memory identity store with two users.  One of the users is an ADMIN
 * and the other is a USER.  
 * 
 * To utilize database authentication, use @DatabaseIdentityStoreDefinition
 * 
 * To utilize LDAP authentication, use @LdapIdentityStoreDefinition
 * 
 * @author Juneau
 */
@ApplicationScoped
public class AcmeIdentityStore implements IdentityStore {
 
    @Override
    public CredentialValidationResult validate(Credential credential) {
 
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
 
        if (login.getCaller().equals("duke") 
                       && login.getPasswordAsString().equals("java2020")) {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        } else if (login.getCaller().equals("jane") 
                       && login.getPasswordAsString().equals("doe")) {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        } else {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }
}