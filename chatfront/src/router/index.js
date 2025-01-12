import {createRouter, createWebHistory} from 'vue-router';
import { memberRouter } from './memberRouter';
const routes = [
    ...memberRouter
]

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;