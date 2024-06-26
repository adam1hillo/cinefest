"use strict"

import {toon, byId, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function() {
    const jaarInput = byId("jaar");
    verbergFilmsEnFouten();
    if (jaarInput.checkValidity()) {
        findByJaar(jaarInput.value);
    } else {
        toon("jaarFout");
        jaarInput.focus();
    }
}

function verbergFilmsEnFouten() {
    verberg("jaarFout");
    verberg("filmsTable");
    verberg("storing");
}

async function findByJaar(jaar) {
    const response = await fetch(`films?jaar=${jaar}`)
    if (response.ok) {
        const films = await response.json();
        toon("filmsTable");
        const filmsBody = byId("filmsBody");
        verwijderChildElementenVan(filmsBody);
        for (const film of films) {
            const tr = filmsBody.insertRow()
            tr.insertCell().innerText = film.id;
            tr.insertCell().innerText = film.titel;
            tr.insertCell().innerText = film.jaar;
            tr.insertCell().innerText = film.vrijePlaatsen;
        }
    } else {
        toon("storing");
    }
}