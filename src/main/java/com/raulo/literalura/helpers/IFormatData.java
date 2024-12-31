package com.raulo.literalura.helpers;

public interface IFormatData {
    <T> T format(String json, Class<T> clase);
}
