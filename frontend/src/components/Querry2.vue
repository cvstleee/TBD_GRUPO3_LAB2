<template>
    <div>
        <h1>Determinar si una ubicación de entrega de un pedido está dentro de una zona restringida</h1>
        
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
import { onMounted, ref } from 'vue';

const locationInput = ref(null);
  const mapContainer = ref(null);
  const latitude = ref(null);
  const longitude = ref(null);
  
  const keyMaps = import.meta.env.VITE_API_GOOGLE_MAPS;

    onMounted(async () => {
        cargarMapa();
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



</script>

<style scoped>
h1 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
}

form {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 5px;
}

input {
    margin-bottom: 10px;
    padding: 5px;
    width: 100%;
    box-sizing: border-box;
}

button {
    padding: 10px 15px;
    background-color: #007BFF;
    color: white;
    border: none;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

p {
    font-size: 18px;
    font-weight: bold;
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