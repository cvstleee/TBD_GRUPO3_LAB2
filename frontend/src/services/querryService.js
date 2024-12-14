import httpClient from "../Utils/http-common";


export const querry1 = async (id) => {
    try {
        const response = await httpClient.get(`api/v1/distributors/getByComuna//${id}`);        
        return { data: response.data, status: response.status };
    } catch (error) {
        throw new Error(
        error.response ? error.response.data : "Error al obtener comunas"
        );
    }
}