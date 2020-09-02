import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SigoSharedModule } from 'app/shared/shared.module';
import { SigoCoreModule } from 'app/core/core.module';
import { SigoAppRoutingModule } from './app-routing.module';
import { SigoHomeModule } from './home/home.module';
import { SigoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SigoSharedModule,
    SigoCoreModule,
    SigoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SigoEntityModule,
    SigoAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class SigoAppModule {}
