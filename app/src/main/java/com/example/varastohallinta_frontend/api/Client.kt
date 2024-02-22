package com.example.varastohallinta_frontend.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// tällä funktiolla luomme jokaista eri api-paketin tiedostoa varten oman
// retrofit-instanssin
fun createClient(): Retrofit {
    // baseUrl on backendimme urlin alku, jota retrofit käyttää
    // 10.0.0.2 on localhostin osoite Androidissa.
    // jos kirjoitat tähän localhost, se ottaa yhteyden emulaattoriin,
    // ei tietokoneen localhostiin.
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1/")
        // aiemmin mainittiin, että konfiguroimme retrofitin käyttämään Gsonia
        // se rivi on tässä
        // nyt, kun käytämme myöhemmin retrofitiä, se osaa muuttaa kaiken uloslähtevän datan
        // autom. jsoniksi gsonin avulla
        // ja vastaavasti osaa muuttaa sisääntulevan jsonista data classeiksi.
        .addConverterFactory(GsonConverterFactory.create()).build()
}