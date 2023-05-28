package org.example.services.impl;

import org.example.enums.SubjectType;
import org.example.exceptions.ITNNotFoundException;
import org.example.exceptions.PassportIsNotValidException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.example.utils.AnotherUtils.*;

@Service
@Transactional
public class FTSService {

    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    private String URI;

    public String getInformationAboutPassport(String passportNumberAndSeries, String APIKey) {

        URI = "https://api-fns.ru/api/mvdpass?docno=" + passportNumberAndSeries
                + "&key=" + APIKey;

        HttpUriRequest httpGet = new HttpGet(URI);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {

            HttpEntity httpEntity = response.getEntity();
            String jsonStringFromHttpEntity = EntityUtils.toString(httpEntity);

            if (jsonStringFromHttpEntity.matches(".*\\bОшибка\\b.*")) {
                throw new PassportIsNotValidException(jsonStringFromHttpEntity);
            }

            JSONObject resultJsonFromString = new JSONObject(jsonStringFromHttpEntity);
            String result = (String) resultJsonFromString.get("result");

            if (result.equals(NOT_APPEAR_AMONG_THE_INVALID_ONES)) {
                return OK;
            } else {
                throw new PassportIsNotValidException();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getINN(
            String lastName,
            String firstName,
            String patronymic,
            String birthdayDate,
            String passportNumberAndSeries,
            String APIKey) {
        URI = "https://api-fns.ru/api/innfl?fam=" + lastName
                + "&nam=" + firstName
                + "&otch=" + patronymic
                + "&bdate=" + birthdayDate
                + "&doctype=21&docno=" + passportNumberAndSeries
                + "&key=" + APIKey;

        HttpUriRequest httpGet = new HttpGet(URI);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {

            JSONObject resultJsonFromString = getJsonFromHttpEntity(response);
            JSONArray items = (JSONArray) resultJsonFromString.get("items");

            if (!items.isEmpty()) {
                return (String) items.getJSONObject(0).get("ИНН");
            } else {
                throw new ITNNotFoundException();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SubjectType getInformationAboutFL(String INN, String APIKey) {

        URI = "https://api-fns.ru/api/fl_status?inn=" + INN
                + "&key=" + APIKey;

        HttpUriRequest httpGet = new HttpGet(URI);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {

            JSONObject resultJsonFromString = getJsonFromHttpEntity(response);

            Boolean self_employment = (Boolean) resultJsonFromString.
                    getJSONObject("Самозанятость").get("Статус");

            Boolean individualEntrepreneur = (Boolean) resultJsonFromString.
                    getJSONObject("ИП").get("Статус");

            if (self_employment)
                return SubjectType.SELF_EMPLOYMENT;

            if (individualEntrepreneur)
                return SubjectType.INDIVIDUAL;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SubjectType.NON;
    }

    private JSONObject getJsonFromHttpEntity(CloseableHttpResponse response) throws IOException {
        HttpEntity httpEntity = response.getEntity();
        String jsonStringFromHttpEntity = EntityUtils.toString(httpEntity);
        return new JSONObject(jsonStringFromHttpEntity);
    }
}
