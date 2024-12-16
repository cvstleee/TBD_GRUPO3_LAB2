<template>
    <div class="container">
      <h1 class="title">Comunas Restringidas</h1>
  
      <div class="dropdown-container">
        <h2>Agregar Comuna Restringida</h2>
        <select v-model="postcommuneid" class="dropdown">
          <option value="" disabled selected>Seleccione una comuna</option>
          <option v-for="comuna in comunasNoRestringidas" :key="comuna.id" :value="comuna.id">
            {{ comuna.comuna }}
          </option>
        </select>
      </div>
  
      <div class="button-container">
        <button class="action-button" @click="addRestrictedCommune(postcommuneid)">Agregar</button>
      </div>
  
      <div class="dropdown-container">
        <h2>Eliminar Comuna Restringida</h2>
        <select v-model="deletecommuneid" class="dropdown">
          <option value="" disabled selected>Seleccione una comuna</option>
          <option v-for="comuna in comunasRestringidas" :key="comuna.communeId" :value="comuna.communeId">
            {{ comuna.comuna }}
          </option>
        </select>
      </div>
  
      <div class="button-container">
        <button class="action-button" @click="removeRestrictedCommune(deletecommuneid)">Eliminar</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import {
    postRestrictedCommune,
    deleteRestrictedCommune,
    getComunasRestringidas
  } from '../services/restrictedCommune';
  import { getComunasNoRestringidas, getComunas } from '../services/comunaService';
  
  const postcommuneid = ref('');
  const deletecommuneid = ref('');

  const comunasNoRestringidas = ref([]);
  const comunasRestringidas = ref([]);
  
  onMounted(async () => {
    await fetchComunasNoRestringidas();
    await fetchComunasRestringidas();
  });
  
  const fetchComunasNoRestringidas = async () => {
    try {
      const response = await getComunasNoRestringidas();
      comunasNoRestringidas.value = response.data;
    } catch (error) {
      console.error('Error al obtener comunas no restringidas:', error);
    }
  };
  
  const fetchComunasRestringidas = async () => {
    try {
      const restrictedResponse = await getComunasRestringidas();
      const allComunasResponse = await getComunas();
  
      const restrictedComunas = restrictedResponse.data;
      const allComunas = allComunasResponse.data;
  
      comunasRestringidas.value = restrictedComunas.map((restricted) => {
        const comunaDetails = allComunas.find(
          (comuna) => comuna.id === restricted.communeId
        );
        return {
          ...restricted,
          comuna: comunaDetails ? comunaDetails.comuna : 'Desconocido'
        };
      });
    } catch (error) {
      console.error('Error al obtener las comunas:', error);
    }
  };
  
  const addRestrictedCommune = async (id) => {
    try {
      const response = await postRestrictedCommune(id);
      if (response.status === 200) {
        alert('Comuna restringida agregada');
        await fetchComunasNoRestringidas();
        await fetchComunasRestringidas();
      } else {
        alert('Error al agregar comuna restringida');
      }
    } catch (error) {
      console.error('Error al agregar comuna restringida:', error);
      alert('Error al agregar comuna restringida');
    }
    postcommuneid.value = '';
  };
  
  const removeRestrictedCommune = async (id) => {
    try {
      const response = await deleteRestrictedCommune(id);
      if (response.status === 200) {
        alert('Comuna restringida eliminada');
        await fetchComunasNoRestringidas();
        await fetchComunasRestringidas();
      } else {
        alert('Error al eliminar comuna restringida');
      }
    } catch (error) {
      console.error('Error al eliminar comuna restringida:', error);
      alert('Error al eliminar comuna restringida');
    }
    deletecommuneid.value = '';
  };
  </script>
  
  <style>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
  }
  
  .title {
    font-size: 24px;
    color: #333;
    margin-bottom: 20px;
  }
  
  .dropdown-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin: 20px 0;
    width: 100%;
    max-width: 400px;
  }
  
  .dropdown-container h2 {
    margin-bottom: 10px;
    color: #555;
  }
  
  .dropdown {
    width: 100%;
    height: 40px;
    font-size: 16px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
    color: #333;
  }
  
  .dropdown option {
    color: #333;
  }
  
  .button-container {
    margin: 20px 0;
  }
  
  .action-button {
    font-size: 16px;
    padding: 10px 20px;
    background-color: #333;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
  }
  
  .action-button:hover {
    background-color: #555;
    transform: scale(1.05);
  }
  
  .action-button:active {
    background-color: #222;
    transform: scale(0.95);
  }
  </style>
  