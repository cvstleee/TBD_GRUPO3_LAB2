<template>
    <div class="confirm-order">
      <h1>Confirmación de Orden</h1>
      <p>Número de Orden: {{ orderID }}</p>
      <p>Nombre de Usuario: {{ user.name }}</p>
      <p>Correo de contacto: {{ user.email }}</p>
      <p>Total de la Orden: {{ total }}</p>
  
      <div class="address-selection">
        <h2>Seleccionar Dirección</h2>
        <input 
          type="text" 
          placeholder="Ingresa una dirección" 
          ref="locationInput" 
        />
        <div ref="mapContainer" class="map"></div>
        <p>Latitud: {{ latitude }}</p>
        <p>Longitud: {{ longitude }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useStore } from 'vuex';
  import { getUser } from '../services/clientService';
  import { orderService } from '../services/orderService';
  
  const locationInput = ref(null);
  const mapContainer = ref(null);
  const latitude = ref(null);
  const longitude = ref(null);
  
  const keyMaps = import.meta.env.VITE_API_GOOGLE_MAPS;
  
  const store = useStore();
  const orderID = store.getters.getOrderId;
  const userId = store.getters.getUserId;
  
  const total = ref(0);
  const user = ref({});
  
  onMounted(async () => {    
    
    cargarMapa();
    obtenerOrden();
    obtenerUsuario();
  });

  const cargarMapa = async () =>{
    const { Loader } = await import('@googlemaps/js-api-loader');
    const loader = new Loader({
      apiKey: keyMaps,
      libraries: ['places'],
    });
  
    loader.load().then(() => {
      const google = window.google;
  
      const map = new google.maps.Map(mapContainer.value, {
        center: { lat: -33.437787, lng: -70.650188 },
        zoom: 11,
      });
  
      const autocomplete = new google.maps.places.Autocomplete(locationInput.value, {
        fields: ['geometry'],
        types: ['address'],
      });
  
      const marker = new google.maps.Marker({ map });
  
      autocomplete.addListener('place_changed', () => {
        const place = autocomplete.getPlace();
        if (!place.geometry) {
          alert('No se encontraron detalles para la dirección ingresada.');
          return;
        }
  
        const location = place.geometry.location;
        map.setCenter(location);
        map.setZoom(15);
        marker.setPosition(location);
  
        latitude.value = location.lat();
        longitude.value = location.lng();
      });
    });
  }
  
  const obtenerOrden = async () => {
    try {
      const response = await orderService.gerOrderById(orderID);
      total.value = response.total;
    } catch (error) {
      console.error('Error al obtener la orden:', error);
    }
  };
  
  const obtenerUsuario = async () => {
    try {
      const response = await getUser(userId);
      user.value = response.data;
    } catch (error) {
      console.error('Error al obtener el usuario:', error);
    }
  };
  </script>
  
  <style scoped>
  .confirm-order {
    text-align: center;
    margin-top: 20px;
  }
  
  .confirm-order h1 {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .confirm-order p {
    font-size: 18px;
    margin: 5px 0;
  }
  
  .address-selection {
    max-width: 800px;
    margin: 0 auto;
  }
  
  .address-selection h2 {
    text-align: center;
  }
  
  .address-selection input {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    font-size: 16px;
  }
  
  .map {
    width: 100%;
    max-width: 600px;
    height: 300px;
    border: 1px solid #ddd;
    margin: 0 auto;
  }
  </style>
  