<template>
    <div>
      <h1>Listar los clientes que se encuentran a más de 50 km del almacén más cercano para analizar zonas con bajo servicio</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Correo</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(cliente, index) in clientes" :key="cliente.id">
            <td>{{ cliente.id }}</td>
            <td>{{ cliente.name }}</td>
            <td>{{ cliente.email }}</td>
          </tr>
        </tbody>
      </table>
  
      <!-- Contenedor del mapa -->
      <div ref="mapContainer" class="map"></div>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import { getAllUsers } from '../services/clientService';
  
  const clientes = ref([]);
  
  // Referencia al contenedor del mapa
  const mapContainer = ref(null);
  
  // Clave API de Google Maps
  const keyMaps = import.meta.env.VITE_API_GOOGLE_MAPS;
  
  onMounted(async () => {
    await getUsers();
    cargarMapa();
  });
  
  const getUsers = async () => {
    try {
      const response = await getAllUsers();
      clientes.value = response.data;
    } catch (error) {
      console.error('Error al obtener los usuarios:', error);
    }
  };
  
  const cargarMapa = async () => {
    const { Loader } = await import('@googlemaps/js-api-loader');
    const loader = new Loader({
      apiKey: keyMaps,
      libraries: [],
    });
  
    loader.load().then(() => {
      const google = window.google;
  
      // Inicializar el mapa centrado en un punto general
      const map = new google.maps.Map(mapContainer.value, {
        center: { lat: -33.4489, lng: -70.6693 }, // Centro en Santiago
        zoom: 10,
      });
  
      // Añadir marcadores para cada cliente
      clientes.value.forEach(cliente => {
        new google.maps.Marker({
          position: { lat: cliente.latitude, lng: cliente.longitude },
          map,
          label: `${cliente.id}`, // Muestra el ID del cliente como etiqueta
        });
      });
    });
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
    text-align: left;
  }
  
.map {
    width: 60%;
    margin-left: 20%;
    height: 300px;
    margin-top: 20px;
}
  </style>
  