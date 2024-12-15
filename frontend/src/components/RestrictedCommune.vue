<template>
    <div>
        <h1>Comunas restringidas</h1>
        <div>
            <h2>Agregar comuna restringida</h2>
            <input type="text" id="addCommuneid" placeholder="ID de comuna" v-model="postcommuneid" />
            <button @click="addRestrictedCommune">Agregar comuna restringida</button>
        </div>
        <div>
            <h2>Eliminar comuna restringida</h2>
            <input type="text" id="deleteCommuneid" placeholder="ID de comuna" v-model="deletecommuneid" />
            <button @click="removeRestrictedCommune">Eliminar comuna restringida</button>
        </div>
    </div>
</template>

<script setup>
import { postRestrictedCommune, deleteRestrictedCommune } from '../services/restrictedCommune';
import { ref } from 'vue';

const postcommuneid = ref('');
const deletecommuneid = ref('');


const addRestrictedCommune = async () => {
    try {
        console.log(postcommuneid.value);
        const response = await postRestrictedCommune(postcommuneid.value);
        if (response.status === 200) {
            alert("Comuna restringida agregada");
        } else {
            alert("Error al agregar comuna restringida");
        }
    } catch (error) {
        console.error("Error al agregar comuna restringida:", error);
        alert("Error al agregar comuna restringida");
    }
};

const removeRestrictedCommune = async () => {
    try {
        console.log(deletecommuneid.value);
        const response = await deleteRestrictedCommune(deletecommuneid.value);
        if (response.status === 200) {
            alert("Comuna restringida eliminada");
        } else {
            alert("Error al eliminar comuna restringida");
        }
    } catch (error) {
        console.error("Error al eliminar comuna restringida:", error);
        alert("Error al eliminar comuna restringida");
    }
};
</script>

<style></style>
