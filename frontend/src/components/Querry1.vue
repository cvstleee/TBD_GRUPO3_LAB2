<template>
    <div class="container">
        <h1 class="title">Listar los repartidores que han entregado pedidos en una zona específica definida por un polígono (zona de reparto)</h1>
        
        <div class="dropdown-container">
            <h2>Seleccione comuna</h2>
            <select v-model="selectedComuna" class="dropdown">                
                <option value="" disabled selected>Seleccione una comuna</option>
                <option v-for="comuna in comunas" :key="comuna.id" :value="comuna.id">
                    {{ comuna.comuna }}
                </option>
            </select>
        </div>

        <div class="button-container">
            <button @click="getQuerry1(selectedComuna)" class="search-button">Buscar Repartidores</button>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID del Repartidor</th>
                    <th>Nombre del Repartidor</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="repartidor in repartidores" :key="repartidor.id">
                    <td>{{ repartidor.id }}</td>
                    <td>{{ repartidor.name }}</td>
                </tr>
            </tbody>
        </table>        
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getComunas } from '../services/comunaService';
import { querry1 } from '../services/querryService';

const repartidores = ref([]);
const comunas = ref([]);
const selectedComuna = ref(1);

onMounted(() => {
    getComunasSantiago();
});

const getComunasSantiago = async () => {
    try {
        const response = await getComunas();
        comunas.value = response.data;
    } catch (error) {
        console.error('Error al obtener las comunas:', error);
    }
};

const getQuerry1 = async (id_comuna) => {
    console.log(selectedComuna.value);
    try {
        const response = await querry1(id_comuna);
        repartidores.value = response.data;
        console.log(comunas.value);
    } catch (error) {
        console.error('Error al obtener las comunas:', error);
    }
};
</script>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.title {
    text-align: center;
    margin-bottom: 20px;
}

.dropdown-container {
    display: flex;
    align-items: center;
    margin: 20px 0;
}

.dropdown-container h2 {
    margin-right: 10px;
}

.dropdown {
    width: 300px; 
    height: 40px; 
    font-size: 16px; 
    padding: 5px; 
    border: 1px solid #ccc; 
    border-radius: 5px; 
}

.button-container {
    margin-bottom: 20px;
}

.search-button {
    font-size: 18px; 
    padding: 10px 20px; 
    background-color: #007BFF; 
    color: white; 
    border: none; 
    border-radius: 5px; 
    cursor: pointer;
}

.search-button:hover {
    background-color: #0056b3;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    border: 1px solid #ddd;
    padding: 8px;
}

th {
    background-color: #f2f2f2;
}
</style>
