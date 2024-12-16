import httpClient from "../Utils/http-common";

export const postRestrictedCommune = async (id) => {
  try {
    const response = await httpClient.post(
      `api/v1/comunas/restricted/post/${id}`
    );
    return { data: response.data, status: response.status };
  } catch (error) {
    throw new Error(
      error.response ? error.response.data : "Error al obtener comunas"
    );
  }
};

export const deleteRestrictedCommune = async (id) => {
  try {
    const response = await httpClient.delete(
      `api/v1/comunas/restricted/delete/${id}`
    );
    return { data: response.data, status: response.status };
  } catch (error) {
    throw new Error(
      error.response ? error.response.data : "Error al obtener comunas"
    );
  }
};


export const getComunasRestringidas = async () => {
  try {
      const response = await httpClient.get("api/v1/comunas/restricted/getAll");        
      return { data: response.data, status: response.status };
  } catch (error) {
      throw new Error(
      error.response ? error.response.data : "Error al obtener comunas"
      );
  }
};
