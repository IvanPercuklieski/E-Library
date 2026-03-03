import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "src/app/shared/components/header/header.component";
import { IonHeader } from "@ionic/angular/standalone";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  imports: [IonHeader, HeaderComponent],
})
export class HomePageComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
