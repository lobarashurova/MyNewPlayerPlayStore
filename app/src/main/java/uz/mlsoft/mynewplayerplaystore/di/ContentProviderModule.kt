package uz.mlsoft.mynewplayerplaystore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.mynewplayerplaystore.data.local.content_resolver.ContentResolver
import uz.mlsoft.mynewplayerplaystore.data.local.content_resolver.impl.ContentResolverImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ContentProviderModule {
    @[Binds Singleton]
    fun bindContentProvider(impl: ContentResolverImpl): ContentResolver
}