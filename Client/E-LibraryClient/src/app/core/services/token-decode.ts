import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenDecode {

    // TODO: da ne e any tuku struktura
    decodeJWTToken(token: string): any {
        try{
            return jwtDecode<any>(token);
        } catch (error) {
            console.error('Error decoding JWT token:', error);
            return null;
        }
    }
}
