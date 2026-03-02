import { Injectable, inject } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
    toastController = inject(ToastController);

    async show(
        message: string = 'default message',
        duration: number = 3000,
        color: string = 'primary',
        position: 'top' | 'bottom' | 'middle' = 'bottom'
    ) {
        const toast = await this.toastController.create({
            message: message,
            duration: duration,
            color: color,
            position: position
        });
        toast.present();
    }
}
