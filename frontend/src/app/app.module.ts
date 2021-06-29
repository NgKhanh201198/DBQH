import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TrangchuComponent} from './pages/trangchu/trangchu.component';
import {DangnhapComponent} from './pages/dangnhap/dangnhap.component';
import {ChitietComponent} from './pages/chitiet/chitiet.component';
import {TiepnhanphananhComponent} from './pages/tiepnhanphananh/tiepnhanphananh.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './core/helper/jwt.interceptor';
import {ErrorInterceptor} from './core/helper/error.interceptor';
import {AuthenticationService} from './core/services/authentication.service';
import {AccountService} from './core/services/account.service';
import {LocationsService} from './core/services/locations.service';
import {AuthenticationGuard} from './core/guard/authentication.guard';
import {AdminComponent} from './admin/admin.component';
import {CreateAccoutComponent} from './admin/account/create-accout/create-accout.component';
import {ListAccountComponent} from './admin/account/list-account/list-account.component';
import {UpdateAccountComponent} from './admin/account/update-account/update-account.component';
import {NavbarComponent} from './pages/navbar/navbar.component';
import {NavAdminComponent} from './admin/nav-admin/nav-admin.component';
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
    declarations: [
        AppComponent,
        TrangchuComponent,
        DangnhapComponent,
        ChitietComponent,
        TiepnhanphananhComponent,
        AdminComponent,
        CreateAccoutComponent,
        ListAccountComponent,
        UpdateAccountComponent,
        NavbarComponent,
        NavAdminComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgxPaginationModule
    ],
    providers: [
        AuthenticationGuard,
        AuthenticationService,
        AccountService,
        LocationsService,
        {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
