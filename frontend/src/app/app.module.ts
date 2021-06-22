import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TrangchuComponent} from './pages/trangchu/trangchu.component';
import {DangnhapComponent} from './pages/dangnhap/dangnhap.component';
import {ChitietComponent} from './pages/chitiet/chitiet.component';
import {TiepnhanphananhComponent} from './pages/tiepnhanphananh/tiepnhanphananh.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';

@NgModule({
    declarations: [
        AppComponent,
        TrangchuComponent,
        DangnhapComponent,
        ChitietComponent,
        TiepnhanphananhComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
