<template>
    <div class="container">
        <h1>Listar los repartidores que han entregado pedidos en una zona específica definida por un polígono (zona de reparto)</h1>
        
        
        <div class="dropdown-container">
            <h2>Seleccione comuna</h2>
            <select v-model="selectedComuna" class="dropdown">                
                <option value="" disabled selected>Seleccione una comuna</option>
                <option v-for="comuna in comunas" :key="comuna.id" :value="comuna.id">
                    {{ comuna.comuna }}
                </option>
            </select>
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
import { getAllUsers } from '../services/clientService';
import { getComunas } from '../services/comunaService';
import { querry1 } from '../services/querryService';

const repartidores = ref([]);
const comunas = ref([]);
const selectedComuna = ref(null);

onMounted(() => {
    getUsers();
    getComunasSantiago();
    gerQuerry1();
});

// Cambiar esto por los repartidores

const getComunasSantiago = async () => {
    try {
        const response = await getComunas();
        comunas.value = response.data;
        console.log(comunas.value);
    } catch (error) {
        console.error('Error al obtener las comunas:', error);
    }
};



const gerQuerry1 = async () => {
    try {
        const response = await querry1(selectedComuna);
        repartidores.value = response.data;
        console.log(comunas.value);
    } catch (error) {
        console.error('Error al obtener las comunas:', error);
    }
};

</script>

<style scoped>
/* Centrar el contenido */
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

/* Estilo para el contenedor del desplegable */
.dropdown-container {
    margin: 20px 0;
}

/* Estilo para el desplegable */
.dropdown {
    width: 300px; /* Ancho del desplegable */
    height: 40px; /* Altura del desplegable */
    font-size: 16px; /* Tamaño de la fuente */
    padding: 5px; /* Espaciado interno */
    border: 1px solid #ccc; /* Borde */
    border-radius: 5px; /* Bordes redondeados */
}

/* Estilo de la tabla */
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
