<template>
    <div class="logs">
        <h1>Logs</h1>

        <p v-if="loading" class="loading">Cargando logs...</p>

        <p v-if="error" class="error">{{ error }}</p>

        <div v-if="!loading && !error" v-for="log in logs" :key="log.id" class="log-item">
            <p><strong>Client ID:</strong> {{ log.client_id }}</p>
            <p><strong>Table Name:</strong> {{ log.table_name }}</p>
            <p><strong>Element ID:</strong> {{ log.element_id }}</p>
            <p><strong>Operation:</strong> {{ log.operation }}</p>
            <p><strong>Description:</strong> {{ log.description }}</p>
        </div>
    </div>
</template>

<script setup>
import { getAllLogs } from "../services/logsCategory";
import { ref, onMounted } from "vue";

const logs = ref([]);
const loading = ref(true);
const error = ref(null);

const getLogs = async () => {
    try {
        const { data, status } = await getAllLogs();
        if (status === 200) {
            logs.value = data;
        } else {
            throw new Error("Error al obtener los logs");
        }
    } catch (err) {
        error.value = "No se pudieron cargar los logs. Intenta nuevamente más tarde.";
        console.error(err);
    } finally {
        loading.value = false;
    }
};

onMounted(async () => {
    await getLogs();
});
</script>

<style scoped>
.logs {
    font-family: Arial, sans-serif;
    margin: 20px auto;
    max-width: 800px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
}

.logs h1 {
    text-align: center;
    color: #333;
}

.loading {
    text-align: center;
    color: #007bff;
}

.error {
    text-align: center;
    color: #ff4d4f;
}

.log-item {
    margin-bottom: 20px;
    padding: 15px;
    border: 1px solid #ccc;
    border-radius: 6px;
    background-color: #fff;
}

.log-item p {
    margin: 5px 0;
}

.log-item p strong {
    color: #555;
}
</style>