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
            <button @click="getQuerry2">Comprobar</button>
            <p>Latitud: {{ latitude }}</p>
            <p>Longitud: {{ longitude }}</p>
        </div>

        <div v-if="ubicacionEntrega !== null">
            <p v-if="ubicacionEntrega">Ubicación en zona restringida </p>
            <p v-else>Ubicación en zona de reparto</p>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import {getComunasNoRestringidas} from '../services/comunaService';
import { querry2 } from '../services/querryService';

const locationInput = ref(null);
const mapContainer = ref(null);
const latitude = ref(null);
const longitude = ref(null);

const ubicacionEntrega = ref(null);

const keyMaps = import.meta.env.VITE_API_GOOGLE_MAPS;

onMounted(async () => {
    await cargarMapa();
});

const cargarMapa = async () => {
    const { Loader } = await import('@googlemaps/js-api-loader');
    const loader = new Loader({
        apiKey: keyMaps,
        libraries: ['places'],
    });

    loader.load().then(async () => {
        const google = window.google;

        const map = new google.maps.Map(mapContainer.value, {
            center: { lat: -33.437787, lng: -70.650188 },
            zoom: 8,
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
            map.setZoom(8);
            marker.setPosition(location);

            latitude.value = location.lat();
            longitude.value = location.lng();
        });

        
        try {
            const response = await getComunasNoRestringidas(); 
            const polygonsData = response.data;
            
            polygonsData.forEach(data => {
                const geojson = JSON.parse(data.geom);
                const polygonCoords = geojson.coordinates[0].map(coord => ({ lat: coord[1], lng: coord[0] }));

                const polygon = new google.maps.Polygon({
                    paths: polygonCoords,
                    strokeColor: '#008000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#008000',
                    fillOpacity: 0.35
                });

                polygon.setMap(map);
            });
        } catch (error) {
            console.error('Error al obtener los polígonos:', error);
        }
    });
};


const getQuerry2 = async () => {
    const data = {
        latitude: latitude.value,
        longitude: longitude.value
    };
    console.log(data);
    const response = await querry2(data);
    console.log(response);
    ubicacionEntrega.value = response.data;
};

</script>

<style scoped>
h1 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
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