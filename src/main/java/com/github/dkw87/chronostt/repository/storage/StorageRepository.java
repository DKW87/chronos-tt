package com.github.dkw87.chronostt.repository.storage;


public class StorageRepository {

    public static StorageRepository getInstance() {
        return StorageRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final StorageRepository INSTANCE = new StorageRepository();
    }
    
}
