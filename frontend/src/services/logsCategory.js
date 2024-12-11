import httpClient from "../Utils/http-common";

export const getAllLogs = async () => {
  try {
    const response = await httpClient.get("api/v1/log/getAll");
    return { data: response.data, status: response.status };
  } catch (error) {
    if (error.response) {
      console.error("Error en la respuesta del servidor:", error.response.data);
      return { data: error.response.data, status: error.response.status };
    } else if (error.request) {
      console.error("No se recibió respuesta del servidor:", error.request);
    } else {
      console.error("Error al hacer la solicitud:", error.message);
    }
    throw error;
  }
};
