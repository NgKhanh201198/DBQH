import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {HomeComponent} from './pages/home/home.component';
import {DetailComponent} from './pages/detail/detail.component';
import {RecommendationsComponent} from './pages/recommendations/recommendations.component';

const routes: Routes = [
    {
        path: 'dang-nhap',
        component: LoginComponent
    },
    {
        path: 'trang-chu',
        component: HomeComponent
    },
    {
        path: 'chi-tiet',
        component: DetailComponent
    },
    {
        path: 'tiep-nhan-phan-anh',
        component: RecommendationsComponent
    },
    {
        path: '**',
        redirectTo: 'trang-chu'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
