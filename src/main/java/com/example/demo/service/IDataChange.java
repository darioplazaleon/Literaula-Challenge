package com.example.demo.service;

public interface IDataChange {
    <T> T obtainData(String json, Class<T> clase);
}
