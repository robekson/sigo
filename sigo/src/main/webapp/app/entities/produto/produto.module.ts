import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { ProdutoComponent } from './produto.component';
import { ProdutoDetailComponent } from './produto-detail.component';
import { ProdutoUpdateComponent } from './produto-update.component';
import { ProdutoDeleteDialogComponent } from './produto-delete-dialog.component';
import { produtoRoute } from './produto.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(produtoRoute)],
  declarations: [ProdutoComponent, ProdutoDetailComponent, ProdutoUpdateComponent, ProdutoDeleteDialogComponent],
  entryComponents: [ProdutoDeleteDialogComponent],
})
export class SigoProdutoModule {}
