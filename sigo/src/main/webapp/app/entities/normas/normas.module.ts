import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { NormasComponent } from './normas.component';
import { NormasDetailComponent } from './normas-detail.component';
import { NormasUpdateComponent } from './normas-update.component';
import { NormasDeleteDialogComponent } from './normas-delete-dialog.component';
import { normasRoute } from './normas.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(normasRoute)],
  declarations: [NormasComponent, NormasDetailComponent, NormasUpdateComponent, NormasDeleteDialogComponent],
  entryComponents: [NormasDeleteDialogComponent],
})
export class SigoNormasModule {}
