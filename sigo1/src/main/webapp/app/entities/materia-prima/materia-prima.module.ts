import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { MateriaPrimaComponent } from './materia-prima.component';
import { MateriaPrimaDetailComponent } from './materia-prima-detail.component';
import { MateriaPrimaUpdateComponent } from './materia-prima-update.component';
import { MateriaPrimaDeleteDialogComponent } from './materia-prima-delete-dialog.component';
import { materiaPrimaRoute } from './materia-prima.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(materiaPrimaRoute)],
  declarations: [MateriaPrimaComponent, MateriaPrimaDetailComponent, MateriaPrimaUpdateComponent, MateriaPrimaDeleteDialogComponent],
  entryComponents: [MateriaPrimaDeleteDialogComponent],
})
export class SigoMateriaPrimaModule {}
