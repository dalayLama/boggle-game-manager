package com.playhub.game.boggle.manager.test.utils;

import com.playhub.game.boggle.manager.consts.LocaleConsts;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class LocaleUtil {

    public static String localedMessage(Locale locale, String en, String ru) {
        return LocaleConsts.EN.equals(locale) ? en : ru;
    }

}
