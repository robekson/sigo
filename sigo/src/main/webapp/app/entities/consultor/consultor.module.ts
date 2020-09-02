import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { ConsultorComponent } from './consultor.component';
import { ConsultorDetailComponent } from './consultor-detail.component';
import { ConsultorUpdateComponent } from './consultor-update.component';
import { ConsultorDeleteDialogComponent } from './consultor-delete-dialog.component';
import { consultorRoute } from './consultor.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(consultorRoute)],
  declarations: [ConsultorComponent, ConsultorDetailComponent, ConsultorUpdateComponent, ConsultorDeleteDialogComponent],
  entryComponents: [ConsultorDeleteDialogComponent],
})
export class SigoConsultorModule {}
