import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './core/helper/jwt.interceptor';
import {ErrorInterceptor} from './core/helper/error.interceptor';
import {AuthenticationService} from './core/services/authentication.service';
import {AccountService} from './core/services/account.service';
import {LocationsService} from './core/services/locations.service';
import {AuthenticationGuard} from './core/guard/authentication.guard';
import {AdminComponent} from './admin/admin.component';
import {ListAccountComponent} from './admin/account/list-account/list-account.component';
import {UpdateAccountComponent} from './admin/account/update-account/update-account.component';
import {NavbarComponent} from './pages/navbar/navbar.component';
import {NavAdminComponent} from './admin/nav-admin/nav-admin.component';
import {NgxPaginationModule} from 'ngx-pagination';
import {DetailComponent} from './pages/detail/detail.component';
import {LoginComponent} from './pages/login/login.component';
import {HomeComponent} from './pages/home/home.component';
import {RecommendationsComponent} from './pages/recommendations/recommendations.component';
import {CreateAgencyComponent} from './admin/agency/create-agency/create-agency.component';
import {CreateAccountComponent} from './admin/account/create-account/create-account.component';
import {ListAgencyComponent} from './admin/agency/list-agency/list-agency.component';
import {UpdateAgencyComponent} from './admin/agency/update-agency/update-agency.component';
import { UpdateRecommentdationsComponent } from './pages/update-recommentdations/update-recommentdations.component';

@NgModule({
    declarations: [
        AppComponent,
        AdminComponent,
        CreateAccountComponent,
        ListAccountComponent,
        UpdateAccountComponent,
        NavbarComponent,
        NavAdminComponent,
        DetailComponent,
        LoginComponent,
        HomeComponent,
        RecommendationsComponent,
        CreateAgencyComponent,
        ListAgencyComponent,
        UpdateAgencyComponent,
        UpdateRecommentdationsComponent
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
    exports: [
        NavAdminComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
