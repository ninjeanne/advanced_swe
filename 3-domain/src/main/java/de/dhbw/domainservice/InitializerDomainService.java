package de.dhbw.domainservice;

public interface InitializerDomainService {
    boolean isInitialized();
    void initialize(String ... data);
}
