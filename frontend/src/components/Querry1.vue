<template>
    <div>
        <h1>Listar los repartidores que han entregado pedidos en una zona específica definida por un polígono (zona de reparto)</h1>
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

const repartidores = ref([]);

onMounted(() => {
    getUsers();
});

//Cambiar esto por los repartidores
const getUsers = async () => {
    try {
      const response = await getAllUsers();
      repartidores.value = response.data;
      console.log(repartidores.value);
    } catch (error) {
      console.error('Error al obtener los usuarios:', error);
    }
  };


</script>

<style scoped>
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