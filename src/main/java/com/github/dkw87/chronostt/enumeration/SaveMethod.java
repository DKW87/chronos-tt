package com.github.dkw87.chronostt.enumeration;

/**
 * Used in StorageRepository to determine if saving should be done directly by the calling thread (SYNCHRONOUS)
 * or be delegated to StorageRepositoryThread and picked up from a queue of tasks (ASYNCHRONOUS).
 * SYNCHRONOUS is currently only used to save defaults on first run of application.
 */
public enum SaveMethod {
    ASYNCHRONOUS,
    SYNCHRONOUS
}
