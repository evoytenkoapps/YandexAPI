package ru.evoytenkoapps.yandexapi;

import java.util.List;

/**
 * Created by evv on 19.04.2017.
 */

public interface AsyncResponse
{
    void processFinish(List<String> output);
}
