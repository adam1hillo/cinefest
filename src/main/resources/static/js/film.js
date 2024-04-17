"use strict"
import {toon, setText, byId, verberg} from "./util.js";
let id;

byId("zoek").onclick = async function() {
    byId("nieuweTitel").value = "";
    verbergFilmEnFouten();
    const zoekIdInput = byId("zoekId");
    if (zoekIdInput.checkValidity()) {
        id = zoekIdInput.value;
        findById(zoekIdInput.value)
    } else {
        toon("zoekIdFout");
        zoekIdInput.focus();
    }
}

function verbergFilmEnFouten() {
    verberg("film");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing")
    verberg("nieuweTitelFout");
}

async function findById(id) {
    const response = await fetch(`films/${id}`)
    if (response.ok) {
        const film = await response.json();
        toon("film");
        setText("titel", film.titel);
        setText("jaar", film.jaar);
        setText("vrijePlaatsen", film.vrijePlaatsen);
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}

byId("verwijder").onclick = async function() {
    const zoekIdInput = byId("zoekId");
    const response = await fetch(`films/${id}`, {method: "DELETE"});
    if (response.ok) {
        verbergFilmEnFouten();
        zoekIdInput.value = "";
        zoekIdInput.focus();
    } else {
        toon("storing");
    }
}
async function updateTitel(titel) {
    const response = await fetch(`films/${id}/titel`,
        {
            method: "PATCH",
            headers: {'Content-Type': "text/plain"},
            body: titel
        })
    if (response.ok) {
        setText("titel", titel);
    } else {
        toon("storing");
    }
}
byId("bewaar").onclick = async function() {
    const nieuweTitelInput = byId("nieuweTitel");
    if (nieuweTitelInput.checkValidity()) {
        verberg("nieuweTitelFout");
        updateTitel(nieuweTitelInput.value);
        nieuweTitelInput.value = "";
    } else {
        toon("nieuweTitelFout");
        nieuweTitelInput.focus();
    }
}
