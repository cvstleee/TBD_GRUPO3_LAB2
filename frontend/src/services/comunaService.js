import httpClient from "../Utils/http-common";

export const getComunas = async () => {
    try {
        const response = await httpClient.get("api/v1/comunas");        
        return { data: response.data, status: response.status };
    } catch (error) {
        throw new Error(
        error.response ? error.response.data : "Error al obtener comunas"
        );
    }
};


export const getComunasNoRestringidas = async () => {
    try {
        const response = await httpClient.get("api/v1/comunas/restringidas");        
        return { data: response.data, status: response.status };
    } catch (error) {
        throw new Error(
        error.response ? error.response.data : "Error al obtener comunas"
        );
    }
}