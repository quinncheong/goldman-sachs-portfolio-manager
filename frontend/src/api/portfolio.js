import axios from 'axios';
import { BASE_SERVER_URL, PORTFOLIO_API_PATH } from './apiFactory';

import mockPortfolios from '@data/portfolio.json';

const axiosInstance = axios.create({
    baseURL: BASE_SERVER_URL + PORTFOLIO_API_PATH,
    timeout: 3000,
    headers: {
        'Content-Type': 'application/json'
    }
});

export const getPortfolios = async () => {
    if (process.env.NODE_ENV !== "production" ) {
        console.log(mockPortfolios)
        return mockPortfolios;
    }

    response = await axiosInstance.get('/all');
    return response;
}

export const getPortfolioByPortfoliId = (portfolioId) => {
    return axiosInstance.get('/' + portfolioId);
}