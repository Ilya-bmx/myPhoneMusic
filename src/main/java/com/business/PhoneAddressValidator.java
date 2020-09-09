package com.business;

public class PhoneAddressValidator {
    public boolean validate(String phoneAddress) {
        return phoneAddress.matches("http:[/][/][0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}[/]$");
    }
}
