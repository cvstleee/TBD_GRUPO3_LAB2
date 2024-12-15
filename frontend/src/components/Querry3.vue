<template>
  <div class="container">
      <h1 class="title">Listar los clientes que se encuentran a más de 50 km del almacén más cercano para analizar zonas con bajo servicio</h1>
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

      <div ref="mapContainer" class="map"></div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { getAllUsers } from '../services/clientService';
import { querry3 } from '../services/querryService';
import { getAllStores } from '../services/querryService';

const clientes = ref([]);
const stores = ref([]);
const mapContainer = ref(null);

const keyMaps = import.meta.env.VITE_API_GOOGLE_MAPS;

onMounted(async () => {
  await getUsers();
  await getStores();
  cargarMapa();
});

const getUsers = async () => {
  try {
      const response = await querry3();
      clientes.value = response.data;
      console.log(clientes.value);
  } catch (error) {
      console.error('Error al obtener los usuarios:', error);
  }
};

const getStores = async () => {
  try {
      const response = await getAllStores();
      stores.value = response.data;
      console.log(stores.value);
  } catch (error) {
      console.error('Error al obtener los almacenes:', error);
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

      const map = new google.maps.Map(mapContainer.value, {
          center: { lat: -33.4489, lng: -70.6693 },
          zoom: 10,
      });

      clientes.value.forEach(cliente => {
          new google.maps.Marker({
              position: { lat: cliente.latitude, lng: cliente.longitude },
              map,
              label: `${cliente.id}`,
          });
      });

      stores.value.forEach(store => {
          new google.maps.Marker({
              position: { lat: store.latitude, lng: store.longitude },
              map,
              icon: {
                  path: google.maps.SymbolPath.CIRCLE,
                  scale: 10,
                  fillColor: '#008000',
                  fillOpacity: 1,
                  strokeWeight: 0,
              },
              label: `${store.id}`,
          });

          new google.maps.Circle({
              map,
              center: { lat: store.latitude, lng: store.longitude },
              radius: 50000,
              fillColor: '#008000',
              fillOpacity: 0.05,
              strokeColor: '#008000',
              strokeOpacity: 0.5,
              strokeWeight: 1,
          });
      });
  });
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
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
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
  text-align: left;
}

.map {
  width: 60%;
  height: 300px;
  margin-top: 20px;
}
</style>
