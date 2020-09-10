import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { TipoProcessoComponent } from './tipo-processo.component';
import { TipoProcessoDetailComponent } from './tipo-processo-detail.component';
import { TipoProcessoUpdateComponent } from './tipo-processo-update.component';
import { TipoProcessoDeleteDialogComponent } from './tipo-processo-delete-dialog.component';
import { tipoProcessoRoute } from './tipo-processo.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(tipoProcessoRoute)],
  declarations: [TipoProcessoComponent, TipoProcessoDetailComponent, TipoProcessoUpdateComponent, TipoProcessoDeleteDialogComponent],
  entryComponents: [TipoProcessoDeleteDialogComponent],
})
export class SigoTipoProcessoModule {}
