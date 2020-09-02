import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { CompraComponent } from './compra.component';
import { CompraDetailComponent } from './compra-detail.component';
import { CompraUpdateComponent } from './compra-update.component';
import { CompraDeleteDialogComponent } from './compra-delete-dialog.component';
import { compraRoute } from './compra.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(compraRoute)],
  declarations: [CompraComponent, CompraDetailComponent, CompraUpdateComponent, CompraDeleteDialogComponent],
  entryComponents: [CompraDeleteDialogComponent],
})
export class SigoCompraModule {}
